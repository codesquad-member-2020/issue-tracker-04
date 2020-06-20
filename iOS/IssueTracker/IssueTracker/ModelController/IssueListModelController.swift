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

extension IssueListModelController {
    static func createWithFakeData() -> IssueListModelController {
        let user = User(id: FakeID.userId, name: "모오오오스")
        let issues: IssueCollection = [
            Issue(id: 1, title: "title1", body: nil, owner: user),
            Issue(id: 2, title: "title2", body: "Something", owner: user),
            Issue(id: 3, title: "title3", body: "Special", owner: user)
        ]

        return .init(issues)
    }
}
