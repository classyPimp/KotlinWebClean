class AddIsApprovedToApprovalToApproverLinks < ActiveRecord::Migration[5.1]
  def change
    add_column :approval_to_approver_links, :is_approved, :timestamp
  end
end
