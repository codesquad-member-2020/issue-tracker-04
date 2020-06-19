import Foundation

protocol IssueListModelControllerObserver: class {
    func issueListModelControllerDidUpdate(_ controller: IssueListModelController)
}

class IssueListModelController {
    private(set) var issueCollection: IssueCollection {
        didSet { notifyObservers() }
    }

    private var observations = [ObjectIdentifier: Observation]()

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

extension IssueListModelController {
    private struct Observation {
        weak var observer: IssueListModelControllerObserver?
    }

    func notifyObservers() {
        for (id, observation) in observations {
            guard let observer = observation.observer else {
                observations.removeValue(forKey: id)
                continue
            }
            observer.issueListModelControllerDidUpdate(self)
        }
    }

    func addObserver(_ observer: IssueListModelControllerObserver) {
        let id = ObjectIdentifier(observer)
        observations[id] = Observation(observer: observer)
    }

    func removeObserver(_ observer: IssueListModelControllerObserver) {
        let id = ObjectIdentifier(observer)
        observations.removeValue(forKey: id)
    }
}
