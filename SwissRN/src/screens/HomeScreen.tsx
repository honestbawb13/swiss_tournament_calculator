import React from 'react';
import { View, StyleSheet, FlatList } from 'react-native';
import LinearGradient from 'react-native-linear-gradient';
import { Text, Card, FAB, useTheme } from 'react-native-paper';
import type { NativeStackScreenProps } from '@react-navigation/native-stack';
import type { RootStackParamList } from '../../App';

type Props = NativeStackScreenProps<RootStackParamList, 'Home'>;

const mockTournaments = [
  { id: '1', name: 'City Open 2025', players: 32, rounds: 5 },
  { id: '2', name: 'Winter Classic', players: 16, rounds: 4 },
  { id: '3', name: 'Friday Night Swiss', players: 24, rounds: 5 },
];

export default function HomeScreen({ navigation }: Props) {
  const theme = useTheme();

  return (
    <View style={styles.container}>
      <LinearGradient
        colors={[theme.colors.primary, '#1F1C2C']}
        start={{ x: 0, y: 0 }}
        end={{ x: 1, y: 1 }}
        style={styles.header}
      >
        <Text variant="displaySmall" style={styles.headerTitle}>
          Swiss Tournaments
        </Text>
        <Text variant="titleMedium" style={styles.headerSubtitle}>
          Manage pairings, rounds, and standings
        </Text>
      </LinearGradient>

      <FlatList
        contentContainerStyle={styles.listContent}
        data={mockTournaments}
        keyExtractor={(item) => item.id}
        renderItem={({ item }) => (
          <Card
            style={styles.card}
            mode="elevated"
            onPress={() => navigation.navigate('Tournament', { id: item.id })}
          >
            <Card.Title
              title={item.name}
              titleStyle={{ color: theme.colors.onSurface }}
              subtitle={`${item.players} players • ${item.rounds} rounds`}
            />
            <Card.Content>
              <Text style={{ opacity: 0.8 }}>
                Tap to open standings and pairings
              </Text>
            </Card.Content>
          </Card>
        )}
      />

      <FAB
        icon="plus"
        style={styles.fab}
        label="New Tournament"
        onPress={() => navigation.navigate('Tournament')}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#0B0B10',
  },
  header: {
    paddingTop: 64,
    paddingHorizontal: 20,
    paddingBottom: 24,
    borderBottomLeftRadius: 24,
    borderBottomRightRadius: 24,
    overflow: 'hidden',
  },
  headerTitle: {
    color: 'white',
    fontWeight: '700',
  },
  headerSubtitle: {
    color: 'rgba(255,255,255,0.85)',
    marginTop: 4,
  },
  listContent: {
    paddingHorizontal: 16,
    paddingTop: 16,
    paddingBottom: 120,
  },
  card: {
    marginBottom: 16,
    backgroundColor: 'rgba(21,21,32,0.9)',
  },
  fab: {
    position: 'absolute',
    right: 16,
    bottom: 24,
  },
});
