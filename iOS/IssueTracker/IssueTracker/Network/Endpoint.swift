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

    static var issue: IssueEndpoint = .init()
}

extension Endpoint {
    var url: URL {
        if path.starts(with: "http"), let url = URL(string: path) { return url }

        var components = URLComponents()
        components.scheme = "http"
        components.host = "15.165.66.150"
        components.path = "/api/" + path
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
}

extension Endpoint {
    private func addJWToken(in request: URLRequest) -> URLRequest {
        var request = request
        if let jwt = jwToken {
            request.setValue(jwt, forHTTPHeaderField: "Authorization")
        }

        return request
    }
}
