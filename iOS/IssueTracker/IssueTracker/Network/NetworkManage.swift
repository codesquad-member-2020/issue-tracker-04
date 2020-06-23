import Foundation

class NetworkManager {
    func loadTwitter(using session: URLSession = .shared) {
        let endpoint = Endpoint.oembed()
        let task = session.request(endpoint) { data, response, error in
            if let data = data {
                let json = try? JSONDecoder().decode(Twitter.self, from: data)
                print(endpoint.url)
                print(json)
            }
        }
        task.resume()
    }
}

extension URLSession {
    typealias Handler = (Data?, URLResponse?, Error?) -> Void

    @discardableResult
    func request(_ endpoint: Endpoint, then handler: @escaping Handler) -> URLSessionTask {
        let task = dataTask(with: endpoint.url, completionHandler: handler)
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
