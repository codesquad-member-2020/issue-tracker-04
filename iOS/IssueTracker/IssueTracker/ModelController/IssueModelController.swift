import Foundation

class IssueModelController: Observable {
    // MARK: - Observable

    private(set) var issue: Issue {
        didSet { notifyObservers() }
    }

    var observations = [ObjectIdentifier: Observation]()

    // MARK: - Init

    init(_ issue: Issue) {
        self.issue = issue
    }

    func update(issue: Issue) {
        self.issue = issue
    }
}

extension IssueModelController {
    static func makeWithFakeIssue() -> IssueModelController {
        let user = User(id: FakeID.userId, name: "Foo")
        let issue = Issue(id: FakeID.make(), title: "Fake Title", owner: user)

        return .init(issue)
    }
}
