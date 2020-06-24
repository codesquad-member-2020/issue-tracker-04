import Foundation

protocol CRUD {
    associatedtype ModelType: Model
    associatedtype PartialModelType: Encodable

    var basePath: String { get }
}

extension CRUD {
    var list: Endpoint {
        Endpoint(path: "issues")
    }

    func detail(by id: ID) -> Endpoint {
        Endpoint(path: "issues/\(id)")
    }

    func create(newIssue: PartialModelType) -> Endpoint {
        var endpoint = self.list
        endpoint.httpMethod = .POST
        endpoint.httpBody = endpoint.convertIntoData(from: newIssue)

        return endpoint
    }

    func update(issue: ModelType) -> Endpoint {
        var endpoint = self.detail(by: issue.id)
        endpoint.httpMethod = .PUT
        endpoint.httpBody = endpoint.convertIntoData(from: issue)

        return endpoint
    }

    func delete(id: ID) -> Endpoint {
        var endpoint = self.detail(by: id)
        endpoint.httpMethod = .DELETE

        return endpoint
    }
}
