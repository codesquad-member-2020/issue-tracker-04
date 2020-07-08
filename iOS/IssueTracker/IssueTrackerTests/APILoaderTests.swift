import XCTest
@testable import IssueTracker

class APILoaderTests: XCTestCase {
    let loader: MockRandomUserLoader = .init(session: .shared)

    func testMockLoader() {
        let expectation = XCTestExpectation(description: "Load Random Users")

        loader.loadList { result in
            if case .success(let users) = result {
                print(result)
                XCTAssertEqual(users.results.count, 10)
                expectation.fulfill()
            }
        }

        wait(for: [ expectation ], timeout: 5)
    }

}

class MockRandomUserLoader: APILoader {
    let session: URLSession

    init(session: URLSession) {
        self.session = session
    }

    func loadList(handler: @escaping APIHandler<MockUsers>) {
        let endpoint = Endpoint(path: "https://randomuser.me/api/?inc=name,email&results=10")
        load(using: session, endpoint: endpoint, handler: handler)
    }
}

struct MockUsers: Decodable {
    var results: [MockUser]
}

struct MockUser: Decodable {
    let name: Name
    let email: String

    struct Name: Decodable {
        let title: String
        let first: String
        let last: String
    }
}
