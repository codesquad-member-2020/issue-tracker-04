import UIKit

class IssueInfoTableViewController: UITableViewController {

    @IBOutlet weak var assigneeView: UIView!
    @IBOutlet weak var labelView: UIView!
    @IBOutlet weak var milestoneView: UIView!
    var issueModelController: IssueModelController?
    
    override func viewDidLoad() {
        super.viewDidLoad()
         self.clearsSelectionOnViewWillAppear = false
        setupAssigneeView()
        setupLabelView()
        setupMilestoneView()
    }
    
    private func setupAssigneeView() {
        let assigneeVC = storyboard?.instantiateViewController(identifier: Identifier.ViewController.assigneeCellVC) { coder in
            AssigneeCellViewController(coder: coder)
        }
        assigneeVC?.assignees = issueModelController?.issue.assignees 
        addChild(assigneeVC!)
        assigneeView.addSubview(assigneeVC!.view)
        assigneeVC!.view.frame = assigneeView.bounds
        assigneeVC!.didMove(toParent: self)
    }
    
    private func setupLabelView() {
        let labelVC = storyboard?.instantiateViewController(identifier: Identifier.ViewController.labelCellVC) { coder in
            LabelCellViewController(coder: coder)
        }
        labelVC?.labels = issueModelController?.issue.labels
        addChild(labelVC!)
        labelView.addSubview(labelVC!.view)
        labelVC!.view.frame = labelView.bounds
        labelVC!.didMove(toParent: self)
    }
    
    private func setupMilestoneView() {
        let milestoneVC = storyboard?.instantiateViewController(identifier: Identifier.ViewController.milestoneCellVC) { coder in
            MilestoneCellViewController(coder: coder)
        }
        addChild(milestoneVC!)
        milestoneView.addSubview(milestoneVC!.view)
        milestoneVC!.view.frame = milestoneView.bounds
        milestoneVC!.didMove(toParent: self)
    }

}
