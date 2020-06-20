import Foundation

class IssueModelController: Observable {
    private(set) var issue: Issue {
        didSet { notifyObservers() }
    }

    var observations = [ObjectIdentifier: Observation]()

    init(_ issue: Issue) {
        self.issue = issue
    }

    func update(issue: Issue) {
        self.issue = issue
    }

    func makeNewIssue(_ partial: PartialIssue) -> Issue {
        return makeFakeIssue(partial)
    }
}

extension IssueModelController {
    func makeFakeIssue(_ partial: PartialIssue) -> Issue {
        let user = User(id: FakeID.userId, name: "Foo")
        return Issue(id: FakeID.make(), title: partial.title, body: partial.body, owner: user)
    }
}

