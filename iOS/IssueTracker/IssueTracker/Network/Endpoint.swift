import Foundation

enum HTTPMethod: String {
    case GET, POST, PUT, DELETE
}

struct Endpoint {
    var httpMethod: HTTPMethod = .GET
    var path: String
    var queryItems: [URLQueryItem] = []
    var httpBody: Data? = nil

    var jwToken: String? = UserDefaults.standard.object(forKey: "JWTToken") as? String

    var url: URL {
        var components = URLComponents()
        components.scheme = "http"
        components.host = "15.165.66.150"
        components.path = "/api/v1/" + path
        if !queryItems.isEmpty {
            components.queryItems = queryItems
        }

        guard let url = components.url else {
            preconditionFailure("Invalid URL components: \(components)")
        }

        return url
    }

    var urlRequest: URLRequest {
        var request = URLRequest(url: url)
        request.httpMethod = httpMethod.rawValue
        request.httpBody = httpBody
        request = addJWToken(in: request)

        return request
    }

    private func addJWToken(in request: URLRequest) -> URLRequest {
        var request = request
        if let jwt = jwToken {
            request.setValue(jwt, forHTTPHeaderField: "Authorization")
        }

        return request
    }
}

extension Endpoint {
    static var oAuthLogin: Endpoint {
        Endpoint(path: "https://github.com/login/oauth/authorize?client_id=bdd909bfff2137535182&redirect_uri=http://15.165.66.150/api/callback&scope=user")
    }

    func convertIntoData<T: Encodable>(from model: T) -> Data? {
        return try? JSONEncoder().encode(model)
    }
}

extension Endpoint {
    enum Issues {
        static var list: Endpoint {
            Endpoint(path: "issues")
        }

        static func detail(by id: ID) -> Endpoint {
            Endpoint(path: "issues/\(id)")
        }

        static func listFilter(isOpen: Bool) -> Endpoint {
            Endpoint(path: "filter",
                     queryItems: [ URLQueryItem(name: "open", value: String(isOpen)) ]
            )
        }

        static func create(newIssue: PartialIssue) -> Endpoint {
            var endpoint = Issues.list
            endpoint.httpMethod = .POST
            endpoint.httpBody = endpoint.convertIntoData(from: newIssue)
    
            return endpoint
        }

        static func update(issue: Issue) -> Endpoint {
            var endpoint = Issues.detail(by: issue.id)
            endpoint.httpMethod = .PUT
            endpoint.httpBody = endpoint.convertIntoData(from: issue)

            return endpoint
        }

        static func delete(id: ID) -> Endpoint {
            var endpoint = Issues.detail(by: id)
            endpoint.httpMethod = .DELETE

            return endpoint
        }
    }
}
