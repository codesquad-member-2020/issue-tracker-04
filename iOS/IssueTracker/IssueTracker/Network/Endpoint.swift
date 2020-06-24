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
    static var oAuthLogin: Self {
        Endpoint(path: "https://github.com/login/oauth/authorize?client_id=bdd909bfff2137535182&redirect_uri=http://15.165.66.150/api/callback&scope=user")
    }

    static var issueList: Self {
        Endpoint(path: "issues")
    }

    static func issueDetail(by id: ID) -> Self {
        Endpoint(path: "issues/\(id)")
    }

    static func issueFilter(isOpen: Bool) -> Self {
        Endpoint(path: "filter",
                 queryItems: [ URLQueryItem(name: "open", value: String(isOpen)) ]
        )
    }

    static func createIssue(body: Data) -> Self {
        var endpoint = Endpoint.issueList
        endpoint.httpMethod = .POST
        endpoint.httpBody = body

        return endpoint
    }

    static func updateIssue(id: ID, body: Data) -> Self {
        var endpoint = Endpoint.issueDetail(by: id)
        endpoint.httpMethod = .PUT
        endpoint.httpBody = body

        return endpoint
    }

    static func deleteIssue(id: ID) -> Self {
        var endpoint = Endpoint.issueDetail(by: id)
        endpoint.httpMethod = .DELETE

        return endpoint
    }

    func convertIntoData<T: Encodable>(from model: T) throws -> Data {
        return try JSONEncoder().encode(model)
    }
}
