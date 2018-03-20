class CreateApprovalToApproverLinks < ActiveRecord::Migration[5.1]
  def change
    create_table :approval_to_approver_links do |t|
      t.references :approval, foreign_key: true
      t.references :user, foreign_key: true
      t.timestamps
  end
end
