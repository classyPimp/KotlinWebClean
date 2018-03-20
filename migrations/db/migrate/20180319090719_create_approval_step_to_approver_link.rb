class CreateApprovalStepToApproverLink < ActiveRecord::Migration[5.1]
  def change
    create_table :approval_step_to_approver_links do |t|
      t.references :approval_step, foreign_key: true
      t.references :user, foreign_key: true
      t.timestamps
    end
  end
end
