import Foundation

enum APIError: Error {
    case invalidData
    case networkFailure(Error)

}

class NetworkManager {
    func loadTwitter(using session: URLSession = .shared) {
        let endpoint = Endpoint.oembed()
        let task = session.request(endpoint) { result in
            switch result {
            case .success(let data):
                let json = try? JSONDecoder().decode(Twitter.self, from: data)
                print(endpoint.url)
                print(json)
            case .failure(.invalidData):
                debugPrint("Invalid Data")
            case .failure(.networkFailure(let error)):
                debugPrint(error)
            }
        }
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
