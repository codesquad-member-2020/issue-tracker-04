import XCTest
@testable import IssueTracker

class IssueTrackerTests: XCTestCase {
    func testViewController() {
        let viewController = viewControllerDidLoadView(identifier: "IssueDetailViewController") as! IssueDetailViewController

        let issue: Issue = .init(id: 1, title: "첫 번째 이슈 테스트", body: "이슈 한 바퀴 \n이슈 목록 만들기", owner: User(id: 1, name: "Jane Doe"))
        viewController.issue = issue

        viewController.loadView()
        viewController.viewDidLoad()
        XCTAssertNotNil(viewController.titleLabel)
        XCTAssertEqual(viewController.titleLabel.text, issue.title)
    }
    
    func testConfigureIssueListDataSource() {
        
        // given
        let user = User(id: 1, name: "모오오오스")
        let issueList: IssueCollection = [Issue(id: 1, title: "title1", body: nil, owner: user),Issue(id: 2, title: "title2", body: "Something", owner: user),Issue(id: 3, title: "title3", body: "Special", owner: user)]
        let dataSource = IssueListDataSource(issueList)
        let tableView = UITableView()
        
        // when
        let numberOfRows = dataSource.tableView(tableView, numberOfRowsInSection: 0)
        
        // then
        XCTAssertEqual(numberOfRows, issueList.count)
    }
}
 
private extension IssueTrackerTests {
    func viewControllerDidLoadView(identifier: String) -> UIViewController {
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        let viewController = storyboard.instantiateViewController(identifier: identifier)

        viewController.loadView()
        viewController.viewDidLoad()

        return viewController
    }
}
