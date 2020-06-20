import XCTest
@testable import IssueTracker

class IssueListModelControllerTests: XCTestCase {
    let controller = IssueListModelController.createWithFakeData()

    func testMakeWithPartialIssue() {
        let partialIssue = PartialIssue(title: "Foo", body: "BarBar")
        let issuesCount = controller.issueCollection.count

        controller.addPartialIssue(partialIssue)
        let isContained = controller.issueCollection.contains {
            $0.title == partialIssue.title && $0.body == partialIssue.body
        }

        XCTAssertEqual(controller.issueCollection.count, issuesCount + 1)
        XCTAssertTrue(isContained)
    }

    func testConvertDetailToBriefIssue() {
        let issue = Faker.makeIssue()
        let brief = controller.convertToBriefIssue(from: issue)

        XCTAssertEqual(brief.id, issue.id)
        XCTAssertEqual(brief.title, issue.title)
        XCTAssertEqual(brief.body, issue.body)
    }

    func testConvertBriefToDetail() {
        let brief = BriefIssue(id: 1, title: "Foo", body: "Bar")
        let detail = controller.convertToDetailIssue(from: brief)

        XCTAssertEqual(detail.id, brief.id)
        XCTAssertEqual(detail.title, brief.title)
        XCTAssertEqual(detail.body, brief.body)
        XCTAssertTrue(detail.comments.isEmpty)
    }

    func testFindIssue() {
        let issue = controller.issueCollection[0]

        let foundIssue = controller.findIssue(by: issue.id)

        XCTAssertEqual(issue, foundIssue)
    }

    func testUpdateIssueWithSameValue() {
        let observer = FakeIssueListModelControllerObserver()
        let issue = controller.issueCollection[0]
        controller.addObserver(observer)
        let newIssue = issue

        controller.updateIssue(with: newIssue)

        XCTAssertFalse(observer.isNotified)
    }

    func testUpdateIssueWithDifferentValue() {
        let observer = FakeIssueListModelControllerObserver()
        let issue = controller.issueCollection[0]
        controller.addObserver(observer)
        var newIssue = issue

        newIssue.title = "Title is edited"
        controller.updateIssue(with: newIssue)

        XCTAssertTrue(observer.isNotified)
    }
}

class FakeIssueListModelControllerObserver: Observer {
    var isNotified = false

    func ObservingObjectDidUpdate() {
        isNotified = true
    }
}
