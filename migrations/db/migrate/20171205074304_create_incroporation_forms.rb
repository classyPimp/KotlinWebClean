class CreateIncroporationForms < ActiveRecord::Migration[5.1]
  def change
    create_table :incorporation_forms do |t|
      t.string :name
      t.string :name_short
      t.timestamps
    end
  end
end
