import Foundation

protocol Observer: class {
    func ObservingObjectDidUpdate()
}

struct Observation {
    weak var observer: Observer?
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
