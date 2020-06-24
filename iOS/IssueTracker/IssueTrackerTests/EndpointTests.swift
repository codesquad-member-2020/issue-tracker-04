import XCTest
@testable import IssueTracker

class EndpointTests: XCTestCase {
    let prefixPath = "http://15.165.66.150/api/v1/"

    func testDefaultURL() {
        let endpoint = Endpoint(path: "")
        XCTAssertEqual(prefixPath, endpoint.url.absoluteString)
    }

    func testURLRequest() {
        let endpoint = Endpoint.issue.list
        let urlRequest = endpoint.urlRequest

        XCTAssertEqual(urlRequest.httpMethod, "GET")
    }

    func testIssueList() {
        let endpoint = Endpoint.issue.list

        let expectedURL = URL(string: prefixPath + "issues")
        XCTAssertEqual(expectedURL, endpoint.url)
    }

    func testDetailIssue() {
        let id = 1
        let endpoint = Endpoint.issue.detail(by: id)
        let expectedURL = URL(string: prefixPath + "issues/\(id)")
        XCTAssertEqual(expectedURL, endpoint.url)
    }

    func testCreateIssue() throws {
        let newIssue = PartialIssue(title: "Foo", body: "Bar")
        let endpoint = Endpoint.issue.create(newIssue)
        let body = try JSONEncoder().encode(newIssue)

        XCTAssertEqual(endpoint.urlRequest.url, URL(string: prefixPath + "issues"))
        XCTAssertEqual(endpoint.urlRequest.httpBody, body)
        XCTAssertEqual(endpoint.urlRequest.httpMethod, "POST")
    }

    func testUpdateIssue() throws {
        var issue = Faker.makeIssue()
        let title = "Update Title"

        issue.title = title
        let data = try JSONEncoder().encode(issue)
        let urlRequest = Endpoint.issue.update(issue).urlRequest

        XCTAssertEqual(urlRequest.url, URL(string: prefixPath + "issues/\(issue.id)"))
        XCTAssertEqual(urlRequest.httpMethod, "PUT")
        XCTAssertEqual(urlRequest.httpBody, data)
    }

    func testDeleteIssue() {
        let issue = Faker.makeIssue()

        let urlRequest = Endpoint.issue.delete(id: issue.id).urlRequest

        XCTAssertEqual(urlRequest.url, URL(string: prefixPath + "issues/\(issue.id)"))
        XCTAssertEqual(urlRequest.httpMethod, "DELETE")
    }
}
