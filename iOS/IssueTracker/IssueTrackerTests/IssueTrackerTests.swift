import XCTest
@testable import IssueTracker

class IssueTrackerTests: XCTestCase {
    func testViewController() {
        let viewController = viewControllerDidLoadView(identifier: "VC") as! IssueListViewController

        let issue: Issue = .init(id: 1, title: "첫 번째 이슈 테스트", body: "이슈 한 바퀴 \n이슈 목록 만들기", owner: User(id: 1, name: "Jane Doe"))
        viewController.issue = issue

        viewController.loadView()
        viewController.viewDidLoad()
        XCTAssertNotNil(viewController.titleLabel)
        XCTAssertEqual(viewController.titleLabel.text, issue.title)

    }

    func viewControllerDidLoadView(identifier: String) -> UIViewController {
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        let viewController = storyboard.instantiateViewController(identifier: identifier)

        viewController.loadView()
        viewController.viewDidLoad()

        return viewController
    }
}
