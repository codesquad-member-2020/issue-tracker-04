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
        let nib = UINib(nibName: "IssueCell", bundle: nil)
        tableView.register(nib, forCellReuseIdentifier: IssueCell.identifier)
        
        // when
        let cellRow = 1
        let numberOfRows = dataSource.tableView(tableView, numberOfRowsInSection: 0)
        let secondCell = dataSource.tableView(tableView, cellForRowAt: IndexPath(row: cellRow, section: 0)) as! IssueCell
        
        // then
        XCTAssertEqual(numberOfRows, issueList.count)
        // FIXME: 오류 발생 (nil)
        XCTAssertEqual(secondCell.titleLabel.text, issueList[cellRow].title)
        XCTAssertEqual(secondCell.detailLabel.text, issueList[cellRow].body)
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
