class AddDescriptionToMonetaryObligations < ActiveRecord::Migration[5.1]
  def change
    add_column :monetary_obligations, :description, :string
  end
end
