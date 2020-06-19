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
        let user = User(id: 1, name: "Foo")
        return Issue(id: 1, title: partial.title, body: partial.body, owner: user)
    }
}

// MARK: - Observer Protocols

struct Observation {
    weak var observer: Observer?
}

protocol Observer: class {
    func ObservingObjectDidUpdate()
}

protocol Observable: class {
    var observations: [ObjectIdentifier: Observation] { get set }

    func notifyObservers()
    func addObserver(_ observer: Observer)
    func removeObserver(_ observer: Observer)
}

extension Observable {
    func notifyObservers() {
        for (id, observation) in observations {
            guard let observer = observation.observer else {
                observations.removeValue(forKey: id)
                continue
            }
            observer.ObservingObjectDidUpdate()
        }
    }

    func addObserver(_ observer: Observer) {
        let id = ObjectIdentifier(observer)
        observations[id] = Observation(observer: observer)
    }

    func removeObserver(_ observer: Observer) {
        let id = ObjectIdentifier(observer)
        observations.removeValue(forKey: id)
    }
}
