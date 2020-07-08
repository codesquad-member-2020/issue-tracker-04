import UIKit

class LabelCellViewController: UIViewController {
    
    var labels: [Label]?
    @IBOutlet var labelCollectionView: UICollectionView!
    override func viewDidLoad() {
        labelCollectionView.dataSource = self
    }
}

extension LabelCellViewController: UICollectionViewDataSource {
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return labels?.count ?? 0
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "LabelCollectionViewCell", for: indexPath) as! LabelCollectionViewCell
        cell.nameLabel.text = labels?[indexPath.row].title
        cell.backgroundColor = UIColor(named: "\(labels?[indexPath.row].color)")
        cell.cornerRadius = 15
        
        return cell
    }
    
}
