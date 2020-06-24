import XCTest
@testable import IssueTracker

class IssueLoaderTests: XCTestCase {
    let loader = IssueLoader()

    func testLoadIssueList() {
        loader.loadList(handler: { _ in })
    }

//    func testLoadIssue() {
//        loader.loadIssue(byID: 1)
//    }
//
//    func testMakeNewIssue() {
//        let newIssue = PartialIssue(title: "Foo", body: "Bar")
//        loader.createNewIssue(body: newIssue)
//    }
//
//    func testUpdateIssue() {
//        let issue = Faker.makeIssue()
//        loader.updateIssue(id: 1, body: issue)
//    }
//
//    func testDeleteIssue() {
//        loader.deleteIssue(id: 1)
//    }
}
