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
        return issueList.filter(by: self.user)
    }
    
    func getAssignedIssues() -> IssueCollection {
        return issueList.filter(contains: self.user)
    }
    
    // FIXME: 작동안함
//    func getCommentedIssues() -> IssueCollection {
//        let commentedIssues = issueList.filter{ issue in
//            issue.comments.filter { comment in
//                comment.author == user
//                }}
//        return commentedIssues
//    }
    
}
