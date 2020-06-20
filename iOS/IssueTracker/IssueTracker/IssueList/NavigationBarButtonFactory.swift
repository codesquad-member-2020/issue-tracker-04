import UIKit

struct BarButtonFactory {
    enum Kind: String, CustomStringConvertible {
        var description: String { self.rawValue }
        
        case edit = "Edit"
        case filter = "Filter"
        case cancel = "Cancel"
        case selectAll = "Select All"
        case close = "Close Issue"
        case deselectAll = "Deselect All"
    }

    func create(_ type: Kind) -> UIBarButtonItem {
        UIBarButtonItem(title: type.description)
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
