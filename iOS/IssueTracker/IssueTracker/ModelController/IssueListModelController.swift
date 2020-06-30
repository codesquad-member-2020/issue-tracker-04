import Foundation

class IssueListModelController {
    private(set) var issueCollection: IssueCollection

    init(_ issueCollection: IssueCollection = []) {
        self.issueCollection = issueCollection
    }

    func findIssue(by id: ID) -> Issue? {
        issueCollection.first { $0.id == id }
    }

    func updateIssue(with newIssue: Issue) {
        guard let index = issueCollection.firstIndex(where: { $0 == newIssue }) else { return }

        issueCollection[index] = newIssue
    }
}
