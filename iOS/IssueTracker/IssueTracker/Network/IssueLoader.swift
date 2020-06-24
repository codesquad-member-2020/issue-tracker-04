import Foundation

class IssueLoader: APILoader {
    let session: URLSession

    init(session: URLSession = .shared) {
        self.session = session
    }

    func loadList(handler: @escaping Handler<BriefIssue>) {
        let endpoint = Endpoint.issue.list
        load(using: session, endpoint: endpoint, handler: handler)
    }

    func loadDetail(id: ID, handler: @escaping Handler<Issue>) {
        let endpoint = Endpoint.issue.detail(by: id)
        load(using: session, endpoint: endpoint, handler: handler)
    }

    func create(_ newModel: PartialIssue, handler: @escaping Handler<PartialIssue>) {
        let endpoint = Endpoint.issue.create(newModel)
        load(using: session, endpoint: endpoint, handler: handler)
    }

    func update(_ model: Issue, handler: @escaping Handler<Issue>) {
        let endpoint = Endpoint.issue.update(model)
        load(using: session, endpoint: endpoint, handler: handler)
    }

    func delete(by id: ID, handler: @escaping Handler<Issue>) {
        let endpoint = Endpoint.issue.delete(by: id)
        load(using: session, endpoint: endpoint, handler: handler)
    }
}
