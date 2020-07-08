import UIKit

class TableViewDataSource<Models>: NSObject, UITableViewDataSource where Models: Collection, Models.Index == IndexPath.Index {

    typealias CellConfigurator = (Models.Element, UITableViewCell) -> Void

    var models: Models

    private let reuseIdentifier: String
    private let cellConfigurator: CellConfigurator

    init(models: Models, reuseIdentifier: String, cellConfigurator: @escaping CellConfigurator) {
        self.models = models
        self.reuseIdentifier = reuseIdentifier
        self.cellConfigurator = cellConfigurator
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return models.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let model = models[indexPath.row]
        let cell = tableView.dequeueReusableCell(withIdentifier: reuseIdentifier, for: indexPath)
        cellConfigurator(model, cell)

        return cell
    }

}
