class CreateApprovalRejections < ActiveRecord::Migration[5.1]
  def change
    create_table :approval_rejections do |t|
      t.references :approval_step_to_approver_link, foreign_key: true
      t.string :reason_text
      t.timestamps
    end
  end
end
