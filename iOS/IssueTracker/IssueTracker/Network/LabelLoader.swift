import Foundation

class LabelLoader: APILoader {
    let session: URLSession

    init(session: URLSession = .shared) {
        self.session = session
    }

    func loadList(handler: @escaping APIHandler<LabelWrapper>) {
        let endpoint = Endpoint.label.list
        load(using: session, endpoint: endpoint, handler: handler)
    }

    func loadDetail(id: ID, handler: @escaping APIHandler<Label>) {
        let endpoint = Endpoint.label.detail(by: id)
        load(using: session, endpoint: endpoint, handler: handler)
    }

    func create(_ newModel: Label, handler: @escaping APIHandler<Label>) {
        let endpoint = Endpoint.label.create(newModel)
        load(using: session, endpoint: endpoint, handler: handler)
    }

    func update(_ model: Label, handler: @escaping APIHandler<Label>) {
        let endpoint = Endpoint.label.update(model)
        load(using: session, endpoint: endpoint, handler: handler)
    }

    func delete(by id: ID, handler: @escaping APIHandler<Label>) {
        let endpoint = Endpoint.label.delete(by: id)
        load(using: session, endpoint: endpoint, handler: handler)
    }
}
