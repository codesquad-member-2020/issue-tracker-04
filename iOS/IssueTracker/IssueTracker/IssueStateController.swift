import Foundation

class IssueStateController {
    
    let user: User
    let issue: Issue
    let issueList: IssueCollection
 
    init(user: User, issue: Issue, issueList: IssueCollection = .init()) {
        self.user = user
        self.issue = issue
        self.issueList = issueList
    }
    
    func getOpenIssues() -> IssueCollection {
        return issueList.filter(by: .open)
    }
    
    func getClosedIssues() -> IssueCollection {
        return issueList.filter(by: .closed)
    }

    func getAuthoredIssues() -> IssueCollection {
        return getOpenIssues().filter(by: self.user)

    }
    
    func getAssignedIssues() -> IssueCollection {
        return getOpenIssues().filter(contains: self.user)
    }
    
    func getCommentedIssues() -> IssueCollection {
        return getOpenIssues()
            .filter{$0.comments.contains(author: user)}
    }

}
