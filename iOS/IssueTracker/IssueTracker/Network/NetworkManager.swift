import Foundation

enum APIError: Error {
    case invalidData
    case networkFailure(Error)

}

class IssueLoader {
    typealias Handler = (Result<BriefIssue, APIError>) -> Void
//    typealias RequestDataType = BriefIssue
//    typealias ResponseDataType = BriefIssue

    func loadList(using session: URLSession = .shared, handler: @escaping Handler) {
        let endpoint = Endpoint.issueList
        let task = session.request(endpoint) { result in
            switch result {
            case .success(let data):
                do {
                    let decoded: BriefIssue = try self.parse(data: data)
                    debugPrint(endpoint.url)
                    handler(.success(decoded))
                } catch {
                    debugPrint(error)
                }
            case .failure(let error):
                debugPrint(error)
                handler(.failure(error))
            }
        }
    }

    func parse<U: Decodable>(data: Data) throws -> U {
       return try JSONDecoder().decode(U.self, from: data)
    }
}

extension URLSession {
    @discardableResult
    func request(_ endpoint: Endpoint, then handler: @escaping (Result<Data, APIError>) -> Void) -> URLSessionTask {
        let task = dataTask(with: endpoint.url) { (data, response, error) in
            guard let data = data else {
                return handler(.failure(.invalidData))
            }

            if let error = error {
                handler(.failure(.networkFailure(error)))
            }

            handler(.success(data))
        }
        task.resume()

        return task
    }
}

struct Twitter: Codable {
    let url: URL
    let authorName: String
}

extension Twitter {
    enum CodingKeys: String, CodingKey {
        case url
        case authorName = "author_name"
    }
}
