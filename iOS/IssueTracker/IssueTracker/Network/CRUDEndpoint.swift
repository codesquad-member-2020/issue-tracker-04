import Foundation

protocol CRUDEndpoint {
    associatedtype ModelType: Model
    associatedtype PartialModelType: Encodable

    var basePath: String { get }
}

extension CRUDEndpoint {
    var list: Endpoint {
        Endpoint(path: "issues")
    }

    func detail(by id: ID) -> Endpoint {
        Endpoint(path: "issues/\(id)")
    }

    func create(_ newModel: PartialModelType) -> Endpoint {
        var endpoint = self.list
        endpoint.httpMethod = .POST
        endpoint.httpBody = endpoint.convertIntoData(from: newModel)

        return endpoint
    }

    func update(_ model: ModelType) -> Endpoint {
        var endpoint = self.detail(by: model.id)
        endpoint.httpMethod = .PUT
        endpoint.httpBody = endpoint.convertIntoData(from: model)

        return endpoint
    }

    func delete(by id: ID) -> Endpoint {
        var endpoint = self.detail(by: id)
        endpoint.httpMethod = .DELETE

        return endpoint
    }
}
