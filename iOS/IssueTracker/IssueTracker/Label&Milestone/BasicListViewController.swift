import UIKit

class BasicListViewController: UIViewController {
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var addButton: UIButton!
    @IBOutlet weak var containerView: UIView!
    @IBOutlet weak var tableView: UITableView!

    private(set) var titleLabelText: String = ""

    override func viewDidLoad() {
        super.viewDidLoad()

        titleLabel.text = titleLabelText
    }

    func addFormViewController(_ type: BasicFormViewController.Type) {
        let vc = type.instantiateFromStoryboard(identifier: Identifier.ViewController.basicForm)
        add(vc, inView: containerView)
    }

    // MARK: - IBAction

    @IBAction func addButtonTouched(_ sender: UIButton) { }
}

class LabelListViewController: BasicListViewController {
    override var titleLabelText: String { Name.Title.labelList }
    private var dataSource: TableViewDataSource<[Label]>!
    private let modelController = LabelsModelController()

    override func viewDidLoad() {
        super.viewDidLoad()

        let loader = LabelLoader()
        loader.loadList { result in
            if case .success(let models) = result {
                DispatchQueue.main.async { [weak self] in
                    self?.dataSource = TableViewDataSource<[Label]>(models: models.allData, reuseIdentifier: "LabelCell") { model, cell in
                        cell.textLabel?.text = model.title
                    }
                    self?.tableView.dataSource = self?.dataSource
                    self?.tableView.reloadData()
                }
            }
        }
    }

    override func addButtonTouched(_ sender: UIButton) {
        addFormViewController(LabelFormViewController.self)
    }
}

class MilestoneListViewController: BasicListViewController {
    override var titleLabelText: String { Name.Title.milestoneList }
    private var dataSource: TableViewDataSource<[Milestone]>!
    private let modelController = MilestoneModelController()

    override func viewDidLoad() {
        super.viewDidLoad()

        dataSource = TableViewDataSource<[Milestone]>(models: [], reuseIdentifier: "MilestoneCell") { model, cell in
            cell.textLabel?.text = model.title
        }
        tableView.dataSource = dataSource
    }


    override func addButtonTouched(_ sender: UIButton) {
        addFormViewController(MilestoneFormViewController.self)
    }
}
