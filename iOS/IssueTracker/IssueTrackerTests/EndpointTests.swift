import XCTest
@testable import IssueTracker

class EndpointTests: XCTestCase {
    let basePath = "http://15.165.66.150/api/v1/"

    func testDefaultURL() {
        let endpoint = Endpoint(path: "")
        XCTAssertEqual(basePath, endpoint.url.absoluteString)
    }

    func testURLRequest() {
        let endpoint = Endpoint.issueList
        let urlRequest = endpoint.urlRequest

        XCTAssertEqual(urlRequest.httpMethod, "GET")
    }

    func testIssueList() {
        let endpoint = Endpoint.issueList

        let expectedURL = URL(string: basePath + "issues")
        XCTAssertEqual(expectedURL, endpoint.url)
    }

    func testDetailIssue() {
        let id = 1
        let endpoint = Endpoint.issueDetail(by: id)
        let expectedURL = URL(string: basePath + "issues/\(id)")
        XCTAssertEqual(expectedURL, endpoint.url)
    }

    func testCreateIssue() {
        let issue = Faker.makeIssue()
        let data = try! JSONEncoder().encode(issue)
        let endpoint = Endpoint.createIssue(body: data)

        XCTAssertEqual(endpoint.urlRequest.url, URL(string: basePath + "issues"))
        XCTAssertEqual(endpoint.urlRequest.httpBody, data)
        XCTAssertEqual(endpoint.urlRequest.httpMethod, "POST")
    }

    func testUpdateIssue() {
        var issue = Faker.makeIssue()
        let title = "Update Title"

        issue.title = title
        let data = try! JSONEncoder().encode(issue)
        let urlRequest = Endpoint.updateIssue(id: issue.id, body: data).urlRequest

        XCTAssertEqual(urlRequest.url, URL(string: basePath + "issues/\(issue.id)"))
        XCTAssertEqual(urlRequest.httpMethod, "PUT")
        XCTAssertEqual(urlRequest.httpBody, data)
    }

    func testDeleteIssue() {
        let issue = Faker.makeIssue()

        let urlRequest = Endpoint.deleteIssue(id: issue.id).urlRequest

        XCTAssertEqual(urlRequest.url, URL(string: basePath + "issues/\(issue.id)"))
        XCTAssertEqual(urlRequest.httpMethod, "DELETE")
    }
}
