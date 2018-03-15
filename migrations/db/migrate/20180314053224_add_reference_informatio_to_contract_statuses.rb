class AddReferenceInformatioToContractStatuses < ActiveRecord::Migration[5.1]
  def change
    add_column :contract_statuses, :internal_number, :string
    add_column :contract_statuses, :assigned_number, :string
    add_column :contract_statuses, :formal_date, :datetime
  end
end
