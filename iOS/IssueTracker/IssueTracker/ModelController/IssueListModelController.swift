import Foundation

class IssueListModelController: Observable {
    private(set) var issueCollection: IssueCollection {
        didSet { notifyObservers() }
    }

    var observations = [ObjectIdentifier: Observation]()

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
