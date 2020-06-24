import UIKit

extension TableViewDataSource where Models == CommentCollection {
    static func make(for comments: Models, reuseIdentifier: String = Identifier.Cell.comment) -> TableViewDataSource {
        return TableViewDataSource(models: comments, reuseIdentifier: reuseIdentifier, cellConfigurator: configureCell)
    }

    private static func configureCell(comment: CommentCollection.Element, cell: UITableViewCell) {
        guard let cell = cell as? CommentCell else { return }

        cell.bodyLabel.text = "Foooooo"
        cell.bodyLabel.textColor = .brown
        cell.authorIdLabel.text = "MemMEe"
    }
}
