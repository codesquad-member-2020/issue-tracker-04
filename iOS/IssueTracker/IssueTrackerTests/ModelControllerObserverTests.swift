import XCTest
@testable import IssueTracker

class ModelControllerObserverTests: XCTestCase {
    func testAddAndRemoveObserver() {
        let observer = FakeObserver()
        let controller = FakeModelController()

        controller.addObserver(observer)
        XCTAssertEqual(controller.observations.count, 1)

        controller.removeObserver(observer)
        XCTAssertEqual(controller.observations.count, 0)
    }

    func testNotifyObservers() {
        let observer1 = FakeObserver()
        let observer2 = FakeObserver()
        let controller = FakeModelController()

        controller.addObserver(observer1)
        controller.addObserver(observer2)
        XCTAssertFalse(observer1.isNotified)
        XCTAssertFalse(observer2.isNotified)

        controller.notifyObservers()
        XCTAssertTrue(observer1.isNotified)
        XCTAssertTrue(observer2.isNotified)
    }
}

class FakeObserver: Observer {
    var isNotified = false

    func ObservingObjectDidUpdate() {
        isNotified = true
    }
}

class FakeModelController: Observable {
    var observations: [ObjectIdentifier : Observation] = [:]
}
