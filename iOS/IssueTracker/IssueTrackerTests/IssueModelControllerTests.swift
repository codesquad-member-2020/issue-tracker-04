import XCTest
@testable import IssueTracker

class IssueModelControllerTests: XCTestCase {
    func testUpdateIssue() {
        let issue = Faker.makeIssue()
        let controller = IssueModelController(issue)
        let observer = Faker.ModelControllerObserver()
        controller.addObserver(observer)

        var newIssue = issue
        controller.update(issue: newIssue)
        XCTAssertFalse(observer.isNotified)

        newIssue.title = "Fake all new title"
        controller.update(issue: newIssue)
        XCTAssertTrue(observer.isNotified)
    }
}
