import Foundation

class IssueListModelController: Observable {
    let user = User(id: FakeID.userId, name: "Foo")

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

    func findIndex(by issueId: ID) -> IssueCollection.Index? {
        issueCollection.firstIndex { $0.id == issueId }
    }

    func updateIssue(with newIssue: Issue) {
        guard let index = findIndex(by: newIssue.id) else { return }

        if issueCollection[index] != newIssue {
            issueCollection[index] = newIssue
        }
    }

    func addPartialIssue(_ partial: PartialIssue) {
        let issue = Issue(id: FakeID.make(), title: partial.title, body: partial.body,
                          owner: user)
        issueCollection.insert(newElement: issue, at: 0)
    }

    func convertToBriefIssue(from detail: Issue) -> BriefIssue {
        return BriefIssue(id: detail.id, title: detail.title, body: detail.body)
    }

    func convertToDetailIssue(from brief: BriefIssue) -> Issue {
        return Issue(id: brief.id, title: brief.title, body: brief.body, owner: user)
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
