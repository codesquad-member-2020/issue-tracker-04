import Foundation

struct Endpoint {
    var path: String
    var queryItems: [URLQueryItem] = []

    var url: URL {
        var components = URLComponents()
        components.scheme = "https"
        components.host = "publish.twitter.com"
        components.path = "/" + path
        if !queryItems.isEmpty {
            components.queryItems = queryItems
        }

        guard let url = components.url else {
            preconditionFailure("Invalid URL components: \(components)")
        }

        return url
    }
}

extension Endpoint {
    static var issueOverview: Self {
        Endpoint(path: "IssueOverViewAll")
    }

    static func issueDetail(by id: ID) -> Self {
        Endpoint(path: "issues/\(id)")
    }

    static func issueFilter(isOpen: Bool) -> Self {
        Endpoint(path: "filter",
                 queryItems: [ URLQueryItem(name: "open", value: String(isOpen)) ]
        )
    }

    static func oembed() -> Self {
        Endpoint(path: "oembed",
                 queryItems: [ URLQueryItem(name: "url", value: "https://twitter.com/hellopolicy/status/867177144815804416") ]
        )
    }
}
