class CreateJobPositionDelegations < ActiveRecord::Migration[5.1]
  def change
    create_table :job_position_delegations do |t|
      t.references :delegated_position, foreign_key: {to_table: "job_positions"}
      t.references :delegated_from_user, foreign_key: {to_table: "users"}
      t.references :delegated_to_user, foreign_key: {to_table: "users"}
      t.timestamp :delegated_since
      t.timestamp :delegated_till
      t.timestamps
    end
  end
end
