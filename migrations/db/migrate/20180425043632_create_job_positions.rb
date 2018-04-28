class CreateJobPositions < ActiveRecord::Migration[5.1]
  def change
    create_table :job_positions do |t|
      t.references :parent_job_position, foreign_key: {to_table: "job_positions"}
      t.string :name
      t.boolean :is_department
      t.boolean :is_department_head
      t.boolean :is_unique_position
      t.string :uniqueness_identifier
      t.timestamps
    end
  end
end
