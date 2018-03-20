class CreateApprovals < ActiveRecord::Migration[5.1]
  def change
    create_table :approvals do |t|
      t.references :approvable, polymorphic: true
      t.bigint :last_stage_id
      t.timestamps
    end
  end
end
