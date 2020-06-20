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
    
    func testFilterIssueList() {
        // given
        let user = Faker.makeUser()
        let issue = Faker.makeIssue()
        var issues = Faker.makeIssues()
        let comments = Faker.makeComments()
        issues[0].comments = comments
        let stateController = IssueStateController(user: user, issue: issue, issueList: issues)
        
        // then
        // openIssues
        XCTAssertEqual(stateController.openIssues.count,2)
        XCTAssertTrue(stateController.openIssues.filter{ $0.status == .closed }.isEmpty)
        
        // closedIssues
        XCTAssertEqual(stateController.closedIssues.count,1)
        XCTAssertTrue(stateController.closedIssues.filter{ $0.status == .open }.isEmpty)
        
        // authoredIssues
        XCTAssertEqual(stateController.authoredIssues.count,2)
        XCTAssertTrue(stateController.authoredIssues.filter{$0.owner != user}.isEmpty)
    
        // assignedIssues
        XCTAssertEqual(stateController.assignedIssues.count, 1)
        
        // commentedIssues
        XCTAssertEqual(stateController.commentedIssues.count, 1)
        
    }
    
    func testCommentCollection() {
        //given
        let comments = Faker.makeComments()
        let user = Faker.makeUser()
        
        //then
        XCTAssertEqual(comments.count, 3)
        XCTAssertEqual(comments.filter { $0.author ==  user }.count, 1)
        
    }
    
    func testIssueCollectionFilter() {
        //given
        let user = Faker.makeUser()
        var issues = Faker.makeIssues()
        let comments = Faker.makeComments()
        issues[1].comments = comments
        
        //then
        XCTAssertEqual(issues.filter(comment: user).count,1)
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
