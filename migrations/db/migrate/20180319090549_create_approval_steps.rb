class CreateApprovalSteps < ActiveRecord::Migration[5.1]
  def change
    create_table :approval_steps do |t|
      t.references :approval, foreign_key: true
      t.timestamps
    end
  end
end
