class AddRegardingApprovalToContractStatuses < ActiveRecord::Migration[5.1]
  def change
    add_column :contract_statuses, :pending_approval, :timestamp
    add_column :contract_statuses, :is_approved, :timestamp
  end
end
