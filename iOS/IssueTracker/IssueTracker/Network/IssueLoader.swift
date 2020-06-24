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
}
