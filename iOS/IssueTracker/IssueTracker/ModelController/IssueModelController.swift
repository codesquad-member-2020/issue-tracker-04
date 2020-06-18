import Foundation

protocol IssueModelControllerObserver: class {
    func issueModelControllerDidUpdate(_ controller: IssueModelController)
}

class IssueModelController {
    private(set) var issue: Issue {
        didSet { notifyObservers() }
    }

    private var observations = [ObjectIdentifier: Observation]()

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
    private struct Observation {
        weak var observer: IssueModelControllerObserver?
    }

    func notifyObservers() {
        for (id, observation) in observations {
            guard let observer = observation.observer else {
                observations.removeValue(forKey: id)
                continue
            }
            observer.issueModelControllerDidUpdate(self)
        }
    }

    func add(observer: IssueModelControllerObserver) {
        let id = ObjectIdentifier(observer)
        observations[id] = Observation(observer: observer)
    }

    func remove(observer: IssueModelControllerObserver) {
        let id = ObjectIdentifier(observer)
        observations.removeValue(forKey: id)
    }
}

extension IssueModelController {
    func makeFakeIssue(_ partial: PartialIssue) -> Issue {
        let user = User(id: 1, name: "Foo")
        return Issue(id: 1, title: partial.title, body: partial.body, owner: user)
    }
}
