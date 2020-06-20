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
        guard let index = issueCollection.firstIndex(where: { $0.id == newIssue.id }) else { return }
        
        if issueCollection[index] != newIssue {
            issueCollection[index] = newIssue
        }
    }

    func addPartialIssue(_ partial: PartialIssue) {
        let user = User(id: FakeID.userId, name: "Foo")
        let issue = Issue(id: FakeID.make(), title: partial.title, body: partial.body,
                          owner: user)
        issueCollection.insert(newElement: issue, at: 0)
    }
}

