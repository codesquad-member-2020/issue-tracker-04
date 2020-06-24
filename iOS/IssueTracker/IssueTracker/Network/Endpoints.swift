import Foundation

extension Endpoint {
    static var oAuthLogin: Endpoint {
        Endpoint(path: "https://github.com/login/oauth/authorize?client_id=bdd909bfff2137535182&redirect_uri=http://15.165.66.150/api/callback&scope=user")
    }

    func convertIntoData<T: Encodable>(from model: T) -> Data? {
        return try? JSONEncoder().encode(model)
    }
}

struct IssueEndpoint: CRUD {
    typealias ModelType = Issue
    typealias PartialModelType = PartialIssue

    var basePath: String = "issue"
}

extension IssueEndpoint {
    func listFilter(isOpen: Bool) -> Endpoint {
        Endpoint(path: "filter",
                 queryItems: [ URLQueryItem(name: "open", value: String(isOpen)) ]
        )
    }
}
