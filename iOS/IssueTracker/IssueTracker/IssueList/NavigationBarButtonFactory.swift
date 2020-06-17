import UIKit

struct BarButtonFactory {
    enum Kind {
        case edit
        case filter
        case cancel
        case selectAll
        case close
        case deselectAll
    }

    func create(_ type: Kind) -> UIBarButtonItem {
        switch type {
        case .edit:
            return UIBarButtonItem(title: "Edit")
        case .filter:
            return UIBarButtonItem(title: "Filter")
        case .cancel:
            return UIBarButtonItem(title: "Cancel")
        case .selectAll:
            return UIBarButtonItem(title: "Select All")
        case .close:
            return UIBarButtonItem(title: "Close Issue")
        case .deselectAll:
            return UIBarButtonItem(title: "Deselect All")
        }

    }
}

extension BarButtonFactory {
    struct Group {
        let left: UIBarButtonItem
        let right: UIBarButtonItem
        let bottom: UIBarButtonItem?
    }

    func makeButtonGroup(_ state: IssueListState) -> Group {
        switch state {
        case .normal:
//            let left = create(.filter)
//            let right = create(.edit)
            return Group(left: create(.filter), right: create(.edit), bottom: nil)
        case .edit(let selected):
            switch selected {
            case .some:
                return Group(left: create(.deselectAll), right: create(.cancel), bottom: create(.close))
            case .none:
                return Group(left: create(.selectAll), right: create(.cancel), bottom: nil)
            }
        }
    }
}

extension UIBarButtonItem {
    convenience init(title: String, style: Style = .plain) {
        self.init()
        self.title = title
        self.style = style
    }
}
